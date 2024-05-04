package com.alerdoci.marvelsuperheroes.app.components
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

@Composable
fun CopyableText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    /*@StringRes*/ copyConfirmationMessage: /*Int = R.string.snack_copied,*/ String = "Copied"
) {
  val context = LocalContext.current
  val annotatedString = buildAnnotatedString {
    withStyle(
        style = SpanStyle(color = color, fontSize = fontSize),
    ) {
      append(text)
    }
    append("  ")
    pushStringAnnotation(tag = "copy_icon", annotation = "copy_icon")
    appendInlineContent(
        "copy_icon", "([Copied Text])")
    pop()
  }
  val iconSize = LocalTextStyle.current.fontSize.times(1.5f)

  SelectionContainer {
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
          annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let { span ->
            if (span.tag == "copy_icon") {
              copyToClipboard(context, text, confirmationMessage = copyConfirmationMessage)
            }
          }
        },
        inlineContent =
            mapOf(
                Pair(
                    "copy_icon",
                    InlineTextContent(
                        Placeholder(
                            width = iconSize,
                            height = iconSize,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center)) {
                          Icon(
                              Icons.Default.ContentCopy,
                              contentDescription = null,)
                        },
                )))
  }
}


@Composable
fun ClickableText(
    text: AnnotatedString,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (Int) -> Unit
) {
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
    val pressIndicator =
        Modifier.pointerInput(onClick) {
            detectTapGestures { pos ->
                layoutResult.value?.let { layoutResult ->
                    onClick(layoutResult.getOffsetForPosition(pos))
                }
            }
        }

    BasicText(
        text = text,
        modifier = modifier.then(pressIndicator),
        style = style,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        inlineContent = inlineContent,
        onTextLayout = {
            layoutResult.value = it
            onTextLayout(it)
        })
}

fun copyToClipboard(
    context: Context,
    text: String,
    label: String? = null,
   /* @StringRes*/ confirmationMessage: /*Int? = R.string.copied*/ String = "Copied",
) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(label ?: "", text))
    if (confirmationMessage != null) {
        //Show toast or snackbar
    }
}



@Preview
@Composable
fun OrderCreatedPreview() {
   CopyableText("this is a copyable text")
}
