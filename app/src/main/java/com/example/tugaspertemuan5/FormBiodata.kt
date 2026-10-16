package com.example.tugaspertemuan5

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.fillMaxHeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBiodata(modifier: Modifier = Modifier) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    val selectedSkills = remember { mutableStateListOf<String>() }

    var showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    var showResult by remember { mutableStateOf(false) }
    var resultTrigger by remember { mutableIntStateOf(0) }
    var resultNama by remember { mutableStateOf("") }
    var resultEmail by remember { mutableStateOf("") }
    var resultTglLahir by remember { mutableStateOf("") }
    var resultExperience by remember { mutableStateOf("") }
    var resultSkills by remember { mutableStateOf("") }

    val skills = stringArrayResource(id = R.array.skill_list)
    val experiences = stringArrayResource(id = R.array.experience_list)
    val dateFormat = stringResource(id = R.string.date_format)
    val fullNameFormat = stringResource(id = R.string.full_name_format)
    val skillSeparator = stringResource(id = R.string.skill_separator)
    val displayDuration = integerResource(id = R.integer.result_display_duration).toLong()
    val scrollState = rememberScrollState()

    LaunchedEffect(resultTrigger) {
        if (resultTrigger > 0) {
            showResult = true
            delay(100)
            scrollState.animateScrollTo(scrollState.maxValue)
            delay(displayDuration)
            showResult = false
        }
    }

    val isFormValid = firstName.isNotBlank() && lastName.isNotBlank() &&
            email.isNotBlank() && birthDate.isNotBlank() &&
            experience.isNotBlank() && selectedSkills.isNotEmpty()

    val fieldShape = RoundedCornerShape(dimensionResource(id = R.dimen.field_corner))
    val cardShape = RoundedCornerShape(
        topStart = dimensionResource(id = R.dimen.card_corner),
        topEnd = dimensionResource(id = R.dimen.card_corner)
    )
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.purple_title),
        unfocusedBorderColor = colorResource(id = R.color.field_border),
        focusedTextColor = colorResource(id = R.color.text_primary),
        unfocusedTextColor = colorResource(id = R.color.text_primary)
    )

    Box(modifier = modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = stringResource(id = R.string.cd_header_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.header_height))
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxHeight()
                .padding(
                    top = dimensionResource(id = R.dimen.card_offset_top),
                    start = dimensionResource(id = R.dimen.card_margin_horizontal),
                    end = dimensionResource(id = R.dimen.card_margin_horizontal)
                )
                .shadow(
                    elevation = dimensionResource(id = R.dimen.card_elevation),
                    shape = cardShape
                )
                .background(
                    color = colorResource(id = R.color.white),
                    shape = cardShape
                )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(dimensionResource(id = R.dimen.card_padding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.title_form),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.purple_title)
                )
                Text(
                    text = stringResource(id = R.string.subtitle_form),
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.text_secondary),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_small))
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small))) {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        label = { Text(stringResource(id = R.string.hint_first_name)) },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        label = { Text(stringResource(id = R.string.hint_last_name)) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    shape = fieldShape,
                    colors = fieldColors,
                    label = { Text(stringResource(id = R.string.hint_email)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small))) {
                    OutlinedTextField(
                        value = birthDate,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        label = { Text(stringResource(id = R.string.hint_birth_date)) },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = stringResource(id = R.string.cd_pick_date)
                                )
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = experience,
                            onValueChange = {},
                            readOnly = true,
                            singleLine = true,
                            shape = fieldShape,
                            colors = fieldColors,
                            label = { Text(stringResource(id = R.string.hint_experience)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            experiences.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item) },
                                    onClick = {
                                        experience = item
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))


    }
}