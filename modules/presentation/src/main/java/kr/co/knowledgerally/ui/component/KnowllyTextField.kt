package kr.co.knowledgerally.ui.component

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.knowledgerally.ui.theme.KnowllyTheme

@Composable
fun KnowllyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    label: String = "",
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    maxLength: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val colors = KnowllyTextFieldDefaults.colors

    Column(modifier = modifier) {

        @OptIn(ExperimentalMaterial3Api::class)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            textStyle = KnowllyTheme.typography.body1,
            cursorBrush = SolidColor(colors.cursorColor(isError).value),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            interactionSource = interactionSource,
            visualTransformation = visualTransformation,
            decorationBox = @Composable { innerTextField ->
                KnowllyTextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    isError = isError,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    placeholder = hint,
                    colors = colors,
                )
            }
        )

        val labelColor by colors.labelColor(enabled, isError, interactionSource)
        KnowllyTextFieldLabel(
            modifier = Modifier.padding(top = 2.dp),
            label = label,
            color = labelColor,
            length = value.length,
            maxLength = maxLength
        )
    }
}

@Composable
private fun KnowllyTextFieldLabel(
    modifier: Modifier = Modifier,
    label: String,
    color: Color,
    length: Int,
    maxLength: Int
) {
    Row(modifier = modifier) {
        if (label.isNotBlank()) {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                style = KnowllyTheme.typography.body2,
                color = color,
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        if (maxLength != Int.MAX_VALUE) {
            Text(
                text = "${length}/${maxLength}",
                style = KnowllyTheme.typography.body2,
                color = color,
            )
        }
    }
}

@Immutable
object KnowllyTextFieldDefaults {
    private val BorderThickness = 0.6.dp
    private val ContentPadding = 12.dp
    private val TextFieldShape = RoundedCornerShape(5.dp)

    val colors
        @Composable
        get() = TextFieldDefaults.outlinedTextFieldColors(
            placeholderColor = KnowllyTheme.colors.grayCC,
            textColor = KnowllyTheme.colors.gray44,
            unfocusedBorderColor = KnowllyTheme.colors.grayDD,
            focusedBorderColor = KnowllyTheme.colors.positive,
            errorBorderColor = KnowllyTheme.colors.error,
            errorCursorColor = KnowllyTheme.colors.error,
            errorLabelColor = KnowllyTheme.colors.error,
            unfocusedLabelColor = KnowllyTheme.colors.gray8F,
            focusedLabelColor = KnowllyTheme.colors.gray8F,
            cursorColor = KnowllyTheme.colors.positive,
        )

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun DecorationBox(
        value: String,
        innerTextField: @Composable () -> Unit,
        enabled: Boolean,
        singleLine: Boolean,
        visualTransformation: VisualTransformation,
        interactionSource: InteractionSource,
        isError: Boolean,
        placeholder: String,
        colors: TextFieldColors,
    ) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            innerTextField = innerTextField,
            placeholder = {
                if (value.isEmpty()) {
                    Text(text = placeholder, style = KnowllyTheme.typography.body1)
                }
            },
            singleLine = singleLine,
            isError = isError,
            colors = colors,
            border = {
                TextFieldDefaults.BorderBox(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    shape = TextFieldShape,
                    focusedBorderThickness = BorderThickness,
                    unfocusedBorderThickness = BorderThickness,
                )
            },
            enabled = enabled,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(ContentPadding),
            visualTransformation = visualTransformation
        )
    }
}

@Preview("Default")
@Composable
private fun KnowllyTextFieldPreview() {
    KnowllyTheme {
        KnowllyTextField(
            value = "텍스트",
            onValueChange = { }
        )
    }
}

@Preview("Hint")
@Composable
private fun KnowllyTextFieldPreviewHint() {
    KnowllyTheme {
        KnowllyTextField(
            value = "",
            onValueChange = { },
            hint = "Hint"
        )
    }
}

@Preview("Error")
@Composable
private fun KnowllyTextFieldPreviewError() {
    KnowllyTheme {
        KnowllyTextField(
            value = "텍스트",
            onValueChange = { },
            isError = true
        )
    }
}

@Preview("Multiline")
@Composable
private fun KnowllyTextFieldPreviewMultiline() {
    KnowllyTheme {
        KnowllyTextField(
            value = "텍스트\n텍스트",
            onValueChange = { },
            singleLine = false
        )
    }
}

@Preview("Label")
@Composable
private fun KnowllyTextFieldLabelPreview() {
    KnowllyTheme {
        KnowllyTextField(
            value = "텍스트",
            onValueChange = { },
            label = "message",
            maxLength = 10,
        )
    }
}

@Preview("Label with Error")
@Composable
private fun KnowllyTextFieldLabelPreviewError() {
    KnowllyTheme {
        KnowllyTextField(
            value = "텍스트",
            onValueChange = { },
            label = "message",
            isError = true,
            maxLength = 10,
        )
    }
}
