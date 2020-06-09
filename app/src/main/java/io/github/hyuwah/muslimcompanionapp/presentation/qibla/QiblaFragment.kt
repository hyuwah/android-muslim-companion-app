package io.github.hyuwah.muslimcompanionapp.presentation.qibla

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import io.github.hyuwah.muslimcompanionapp.R
import io.github.hyuwah.muslimcompanionapp.databinding.FragmentQiblaBinding
import io.github.hyuwah.muslimcompanionapp.presentation.base.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Math.toDegrees
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.round

class QiblaFragment : Fragment(R.layout.fragment_qibla), SensorEventListener {

    private val binding by viewBinding(FragmentQiblaBinding::bind)
    private val viewModel by viewModel<QiblaViewModel>()

    private val sensorManager by lazy {
        getSystemService(requireContext(), SensorManager::class.java) as SensorManager
    }

    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null
    private var currentGravity: FloatArray? = null
    private var currentGeomagnetic: FloatArray? = null
    private var currentDegree = 0f
    private val meccaDegree by lazy { viewModel.getMeccaDegree() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        magnetometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            currentGravity = viewModel.applyLowPassFilter(event.values.clone(), currentGravity)
        }
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            currentGeomagnetic = viewModel.applyLowPassFilter(event.values.clone(), currentGeomagnetic)
        }
        if (currentGravity != null && currentGeomagnetic != null) {
            val R = FloatArray(9)
            val I = FloatArray(9)
            val success = SensorManager.getRotationMatrix(R, I, currentGravity, currentGeomagnetic)
            if (success) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(R, orientation)
                var degree = round((toDegrees(orientation[0].toDouble()) + 360).toFloat()) % 360
                val formatter = DecimalFormat("#.#").apply {
                    roundingMode = RoundingMode.CEILING
                }
                val direction = viewModel.getDirection(degree)
                val meccaDirection = viewModel.getDirection(meccaDegree)
                binding.tvCompass.text = "Direction: ${formatter.format(degree)}° $direction\nQibla: ${formatter.format(meccaDegree)}° $meccaDirection"

                rotateCompass(degree)
                rotateQiblaMarker(degree)
                currentDegree = -degree
            }
        }
    }

    private fun rotateCompass(degree: Float) {
        val animation = createRotateAnim(currentDegree, degree)
        binding.ivCompass.startAnimation(animation)
    }

    private fun rotateQiblaMarker(degree: Float) {
        val qiblaAnim = createRotateAnim(currentDegree + meccaDegree, degree - meccaDegree)
        binding.ivQiblaMarker.startAnimation(qiblaAnim)
    }

    private fun createRotateAnim(fromDegree: Float, toDegree: Float): RotateAnimation {
        return RotateAnimation(
                fromDegree,
                -toDegree,
                RELATIVE_TO_SELF, 0.5f,
                RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            fillAfter = true
        }
    }

}