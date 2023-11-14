//package com.alerdoci.marvelsuperheroes.app.components
//
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.res.vectorResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.metehanbolat.uistructurecompose.R
//import com.metehanbolat.uistructurecompose.navigation.GraphScreens
//import com.metehanbolat.uistructurecompose.ui.theme.primaryColor
//import com.metehanbolat.uistructurecompose.ui.theme.whiteBackground
//
//@Composable
//fun RegisterPage(navController: NavController) {
//
//    val nameValue = remember { mutableStateOf("") }
//    val emailValue = remember { mutableStateOf("") }
//    val phoneValue = remember { mutableStateOf("") }
//    val passwordValue = remember { mutableStateOf("") }
//    val confirmPasswordValue = remember { mutableStateOf("") }
//
//    val passwordVisibility = remember { mutableStateOf(false) }
//    val confirmPasswordVisibility = remember { mutableStateOf(false) }
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.BottomCenter,
//        content = {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White),
//                contentAlignment = Alignment.TopCenter,
//                content = {
//                    Image(
//                        painter = painterResource(id = R.drawable.register_page),
//                        contentDescription = null
//                    )
//                }
//            )
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.7f)
//                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
//                    .background(whiteBackground)
//                    .padding(10.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .verticalScroll(rememberScrollState())
//                        .weight(1f, false),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.sign_up),
//                        fontSize = 30.sp,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            letterSpacing = 2.sp
//                        )
//                    )
//                    Spacer(modifier = Modifier.padding(20.dp))
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        OutlinedTextField(
//                            value = nameValue.value,
//                            onValueChange = { nameValue.value = it },
//                            label = { Text(text = stringResource(id = R.string.name)) },
//                            placeholder = { Text(text = stringResource(id = R.string.name)) },
//                            singleLine = true,
//                            modifier = Modifier.fillMaxWidth(0.8f)
//                        )
//
//                        OutlinedTextField(
//                            value = emailValue.value,
//                            onValueChange = { emailValue.value = it },
//                            label = { Text(text = stringResource(id = R.string.email_address)) },
//                            placeholder = { Text(text = stringResource(id = R.string.email_address)) },
//                            singleLine = true,
//                            modifier = Modifier.fillMaxWidth(0.8f)
//                        )
//
//                        OutlinedTextField(
//                            value = phoneValue.value,
//                            onValueChange = { phoneValue.value = it },
//                            label = { Text(text = stringResource(id = R.string.phone_number)) },
//                            placeholder = { Text(text = stringResource(id = R.string.phone_number)) },
//                            singleLine = true,
//                            modifier = Modifier.fillMaxWidth(0.8f),
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
//                        )
//
//                        OutlinedTextField(
//                            value = passwordValue.value,
//                            onValueChange = { passwordValue.value = it },
//                            label = { Text(text = stringResource(id = R.string.password)) },
//                            placeholder = { Text(text = stringResource(id = R.string.password)) },
//                            singleLine = true,
//                            modifier = Modifier.fillMaxWidth(0.8f),
//                            trailingIcon = {
//                                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
//                                    Icon(
//                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_password_eye),
//                                        tint = if (passwordVisibility.value) primaryColor else Color.Gray,
//                                        contentDescription = null
//                                    )
//                                }
//                            },
//                            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
//                        )
//
//                        OutlinedTextField(
//                            value = confirmPasswordValue.value,
//                            onValueChange = { confirmPasswordValue.value = it },
//                            label = { Text(text = stringResource(id = R.string.confirm_password)) },
//                            placeholder = { Text(text = stringResource(id = R.string.confirm_password)) },
//                            singleLine = true,
//                            modifier = Modifier.fillMaxWidth(0.8f),
//                            trailingIcon = {
//                                IconButton(onClick = { confirmPasswordVisibility.value = !confirmPasswordVisibility.value }) {
//                                    Icon(
//                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_password_eye),
//                                        tint = if (confirmPasswordVisibility.value) primaryColor else Color.Gray,
//                                        contentDescription = null
//                                    )
//                                }
//                            },
//                            visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
//                        )
//                        Spacer(modifier = Modifier.padding(18.dp))
//
//                        Button(
//                            modifier = Modifier
//                                .fillMaxWidth(0.8f)
//                                .height(50.dp),
//                            onClick = {  }
//                        ) {
//                            Text(text = stringResource(id = R.string.sign_up), fontSize = 20.sp)
//                        }
//
//                        Spacer(modifier = Modifier.padding(20.dp))
//
//                        Text(
//                            text = stringResource(id = R.string.login_instead),
//                            modifier = Modifier
//                                .clickable {
//                                    navController.navigate(GraphScreens.LOGIN.pageName) {
//                                        popUpTo(navController.graph.startDestinationId)
//                                        launchSingleTop = true
//                                    }
//                                }
//                        )
//
//                        Spacer(modifier = Modifier.padding(20.dp))
//                    }
//                }
//            }
//        }
//    )
//}
//
//@Preview
//@Composable
//fun RegisterPagePreview() {
//    RegisterPage(rememberNavController())
//}
