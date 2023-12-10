package com.alerdoci.marvelsuperheroes.app.common.anim

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

data class RotationState(val pitch: Float, val roll: Float)
@Composable
fun observeDeviceRotation(): RotationState {
    val rotationState = remember { mutableStateOf(RotationState(0f, 0f)) }
    val sensorManager = LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    val rotationMatrix = FloatArray(9)
    val orientationAngles = FloatArray(3)
    val accelerometerReading = FloatArray(3)
    val magnetometerReading = FloatArray(3)
    val accelerometerListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                System.arraycopy(it.values, 0, accelerometerReading, 0, accelerometerReading.size)
                SensorManager.getRotationMatrix(
                    rotationMatrix,
                    null,
                    accelerometerReading,
                    magnetometerReading
                )
                SensorManager.getOrientation(rotationMatrix, orientationAngles)
                rotationState.value = RotationState(
                    pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat(),
                    roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat(),
                )
            }
        }
    }
    val magnetometerListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                System.arraycopy(it.values, 0, magnetometerReading, 0, magnetometerReading.size)
                SensorManager.getRotationMatrix(
                    rotationMatrix,
                    null,
                    accelerometerReading,
                    magnetometerReading
                )
                SensorManager.getOrientation(rotationMatrix, orientationAngles)
                rotationState.value = RotationState(
                    pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat(),
                    roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat(),
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        sensorManager.registerListener(
            accelerometerListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
            magnetometerListener,
            magnetometer,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_UI
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            sensorManager.unregisterListener(accelerometerListener)
            sensorManager.unregisterListener(magnetometerListener)
        }
    }
    return rotationState.value
}
