package org.matias.nocturnatracker.core.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.matias.nocturnatracker.core.theme.NocturnaBorder
import org.matias.nocturnatracker.core.theme.NocturnaGold
import org.matias.nocturnatracker.core.theme.NocturnaSurfaceVariant
import org.matias.nocturnatracker.core.theme.NocturnaTextMuted
import org.matias.nocturnatracker.core.theme.NocturnaTextPrimary

@Composable
fun NocturnaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = singleLine,
        textStyle = TextStyle(
            color = NocturnaTextPrimary,
            fontSize = 15.sp
        ),
        label = label?.takeIf { it.isNotEmpty() }?.let {
            { Text(text = it) }
        },
        placeholder = placeholder.takeIf { it.isNotEmpty() }?.let {
            {
                Text(
                    text = it,
                    color = NocturnaTextMuted,
                    fontSize = 14.sp
                )
            }
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = NocturnaSurfaceVariant,
            unfocusedContainerColor = NocturnaSurfaceVariant,
            focusedBorderColor = NocturnaGold,
            unfocusedBorderColor = NocturnaBorder,
            cursorColor = NocturnaGold,
            focusedLabelColor = NocturnaGold,
            unfocusedLabelColor = NocturnaTextMuted
        )
    )
}
