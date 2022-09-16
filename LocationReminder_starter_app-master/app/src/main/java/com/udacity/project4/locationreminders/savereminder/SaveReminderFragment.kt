package com.udacity.project4.locationreminders.savereminder

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.locationreminders.geofence.GeofenceBroadcastReceiver
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.utils.Permissions
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.android.ext.android.inject
private const val TAG = "SaveReminderFragment"
class SaveReminderFragment : BaseFragment() {
    override val _viewModel: SaveReminderViewModel by inject()
    private lateinit var binding: FragmentSaveReminderBinding
    private lateinit var geofencingClient: GeofencingClient
    private var isBackgroundPermissionOk = false

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(), GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    private var reminderFromViewModel = ReminderDataItem("", "", "", 0.0, 0.0)
    private lateinit var requestPermissionLauncher : ActivityResultLauncher<String>
    private val requestDeviceLocationOn = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        checkDeviceLocationSettings(false)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_save_reminder, container, false)

        setDisplayHomeAsUpEnabled(true)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                isBackgroundPermissionOk = granted
            }
        binding.viewModel = _viewModel
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.selectLocation.setOnClickListener {
            //            Navigate to another fragment to get the user location
            _viewModel.navigationCommand.value =
                NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToSelectLocationFragment())
        }

        binding.saveReminder.setOnClickListener {
            val title = _viewModel.reminderTitle.value
            val description = _viewModel.reminderDescription.value
            val location = _viewModel.reminderSelectedLocationStr.value
            val latitude = _viewModel.latitude.value
            val longitude = _viewModel.longitude.value

//            TODO: use the user entered reminder details to:
//             1) add a geofencing request
//             2) save the reminder to the local db
            reminderFromViewModel = ReminderDataItem(title, description, location, latitude, longitude)

            if (!_viewModel.validateEnteredData(reminderFromViewModel)) {
                Toast.makeText(
                    requireContext(), "ERROR: Invalid reminder data!!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            isBackgroundPermissionOk = Permissions.checkBackgroundLocationPermission(requireContext())

            if (isBackgroundPermissionOk) {
                checkDeviceLocationSettings(true)
                return@setOnClickListener
            }
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    private fun createGeofenceRequest(
        reminder: ReminderDataItem
    ): GeofencingRequest {
        val requestID = reminder.id
        val longitude = reminder.longitude!!
        val latitude = reminder.latitude!!
        val geofence = Geofence.Builder()
            .setRequestId(requestID)
            .setCircularRegion(
                latitude,
                longitude,
                100f
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setNotificationResponsiveness(300000)
            .build()

        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofence(geofence)
        }.build()
    }

    private fun checkDeviceLocationSettings(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())
        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    requestDeviceLocationOn.launch(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(
                        TAG,
                        "Error getting location settings resolution: " + sendEx.message
                    )
                }
            } else {
                Snackbar.make(
                    binding.saveReminder,
                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettings()
                }.show()
            }
        }
        locationSettingsResponseTask.addOnCompleteListener { locationSettingResponse ->
            if (locationSettingResponse.isSuccessful) {
                val geofenceRequest = createGeofenceRequest(reminderFromViewModel)

                if (Permissions.checkLocationPermission(requireContext())) {
                    geofencingClient.addGeofences(geofenceRequest, geofencePendingIntent).run {
                        addOnSuccessListener {
                            _viewModel.saveReminder(reminderFromViewModel)
                        }
                        addOnFailureListener { ex ->
                            Toast.makeText(
                                requireContext(), "Failed to add a geofence",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (ex.message != null) {
                                Log.d(TAG, ex.message!!)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewModel.onClear()
    }
}
