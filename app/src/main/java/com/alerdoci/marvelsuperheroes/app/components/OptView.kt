package com.alerdoci.marvelsuperheroes.app.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

fun getTextList(): List<MutableState<TextFieldValue>> = textList
fun getRequesterList(): List<FocusRequester> = requesterList
const val TEST_VERIFY_CODE = "1234"

private val textList = listOf(
    mutableStateOf(TextFieldValue(text = "", selection = TextRange(0))),
    mutableStateOf(TextFieldValue(text = "", selection = TextRange(0))),
    mutableStateOf(TextFieldValue(text = "", selection = TextRange(0))),
    mutableStateOf(TextFieldValue(text = "", selection = TextRange(0)))
)

private val requesterList = listOf(
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester()
)

@ExperimentalComposeUiApi
@Composable
fun OtpView(
    textList: List<MutableState<TextFieldValue>> = getTextList(),
    requesterList: List<FocusRequester> = getRequesterList()
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.DarkGray
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 50.dp)
                    .align(Alignment.TopCenter)
            ) {
                for (i in textList.indices) {
                    InputView(
                        value = textList[i].value,
                        onValueChange = { newValue ->
                            if (textList[i].value.text != "") {
                                if (newValue.text == "") {
                                    textList[i].value = TextFieldValue(
                                        text = "",
                                        selection = TextRange(0)
                                    )
                                }
                                return@InputView
                            }
                            textList[i].value = TextFieldValue(
                                text = newValue.text,
                                selection = TextRange(newValue.text.length)
                            )
                            connectInputtedCode {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                                if (it) {
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Error, Again!", Toast.LENGTH_SHORT).show()
                                    for (text in textList) {
                                        text.value = TextFieldValue(
                                            text = "",
                                            selection = TextRange(0)
                                        )
                                    }
                                }
                            }
                            nextFocus()
                        },
                        focusRequester = requesterList[i]
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = null, block = {
        delay(300)
        requesterList[0].requestFocus()
    })
}

private fun connectInputtedCode(
    textList: List<MutableState<TextFieldValue>> = getTextList(),
    onVerifyCode: ((success: Boolean) -> Unit)? = null
) {
    var code = ""
    for (text in textList) {
        code += text.value.text
    }

    if (code.length == 4) {
        verifyCode(
            code,
            onSuccess = {
                onVerifyCode?.let {
                    it(true)
                }
            },
            onError = {
                onVerifyCode?.let {
                    it(false)
                }
            }
        )
    }

}

private fun verifyCode(code: String, onSuccess: () -> Unit, onError: () -> Unit) {
    if (code == TEST_VERIFY_CODE) {
        onSuccess()
    } else {
        onError()
    }
}

private fun nextFocus(
    textList: List<MutableState<TextFieldValue>> = getTextList(),
    requesterList: List<FocusRequester> = getRequesterList()
) {
    for (index in textList.indices) {
        if (textList[index].value.text == "") {
            if (index < textList.size) {
                requesterList[index].requestFocus()
                break
            }
        }
    }
}

@Composable
fun InputView(
    value: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    focusRequester: FocusRequester
) {
    BasicTextField(
        readOnly = false,
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .wrapContentSize()
            .focusRequester(focusRequester),
        maxLines = 1,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        },
        cursorBrush = SolidColor(Color.White),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = null)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview(showBackground = true)
fun OtpViewPreview() {
    OtpView()
}
