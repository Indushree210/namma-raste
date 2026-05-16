package com.namma.raste.health.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namma.raste.health.ai.RoadAnalysisResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ScanUiState())

    val uiState: StateFlow<ScanUiState> =
        _uiState.asStateFlow()

    fun onImageSelected(
        uri: Uri,
        context: Context
    ) {

        try {

            val bitmap: Bitmap? = if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
            ) {

                val source =
                    ImageDecoder.createSource(
                        context.contentResolver,
                        uri
                    )

                ImageDecoder.decodeBitmap(source)

            } else {

                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    uri
                )
            }

            bitmap?.let {

                _uiState.value =
                    _uiState.value.copy(
                        selectedBitmap = it,
                        analysisResult = null,
                        error = null
                    )
            }

        } catch (e: Exception) {

            _uiState.value =
                _uiState.value.copy(
                    error = e.message
                )
        }
    }

    fun onBitmapCaptured(bitmap: Bitmap) {

        _uiState.value =
            _uiState.value.copy(
                selectedBitmap = bitmap,
                analysisResult = null,
                error = null
            )
    }

    fun analyzeImage(bitmap: Bitmap) {

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    error = null
                )

            try {

                delay(2000)

                val fakeResult =
                    RoadAnalysisResult(
                        damageType = "Pothole Detected",
                        severity = "High",
                        confidence = "95%",
                        urgency = "Immediate Repair Needed"
                    )

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        analysisResult = fakeResult
                    )

            } catch (e: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        error = e.message
                    )
            }
        }
    }
}

data class ScanUiState(

    val selectedBitmap: Bitmap? = null,

    val isLoading: Boolean = false,

    val analysisResult: RoadAnalysisResult? = null,

    val error: String? = null
)