package com.example.constraintlayoutcomposeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.constraintlayoutcomposeapplication.ui.theme.ConstraintLayoutComposeApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutComposeApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}


@Composable
fun ConstraintLayoutBasic() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (box1, box2, box3) = createRefs()

        Box(modifier = Modifier
            .size(150.dp)
            .background(Color.Red)
            .constrainAs(box1) {

            })

        Box(modifier = Modifier
            .size(100.dp)
            .background(Color.Green)
            .constrainAs(box2) {

            })

        Box(modifier = Modifier
            .size(50.dp)
            .background(Color.Blue)
            .constrainAs(box3) {

            })
    }
}


@Composable
fun LoginWithConstraintLayout() {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val constraints = ConstraintSet {
        val userName = createRefFor("userName")
        val userPassword = createRefFor("password")
        val button = createRefFor("button")
        //username constraint
        constrain(userName) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
        constrain(userPassword) {
            start.linkTo(userName.start)
            top.linkTo(userName.bottom, margin = 10.dp)
            end.linkTo(userName.end)
        }
        constrain(button) {
            start.linkTo(userPassword.start)
            top.linkTo(userPassword.bottom, margin = 10.dp)
            end.linkTo(userPassword.end)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        ConstraintLayout(constraintSet = constraints) {
            TextField(value = username, onValueChange = {
                username = it
            }, Modifier.layoutId("userName"))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                Modifier.layoutId("password"),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(onClick = { /*TODO*/ }, modifier = Modifier.layoutId("button")) {
                Text(text = "Login")

            }
        }
    }


}


@Composable
fun ArrangeItemHorizontally() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (text1, text2, text3) = createRefs()
        Text(text = "Text one", Modifier.constrainAs(text1) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        })
        Text(text = "Text two", Modifier.constrainAs(text2) {
            start.linkTo(text1.start)
            top.linkTo(text1.bottom)
            end.linkTo(text1.end)

        })
        Text(text = "Text three", Modifier.constrainAs(text3) {
            start.linkTo(text2.start)
            top.linkTo(text2.bottom)
            end.linkTo(text2.end)


        })
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//Guideline in Constraint
@Composable
fun GuidelineExample() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (text1) = createRefs()
        val createGuidelineTop = createGuidelineFromTop(40.dp)
        Text(
            text = "Some Contents...",
            modifier = Modifier.constrainAs(text1) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(createGuidelineTop)
            }
        )
    }
}

//Barrier in Constraint
@Composable
fun CreateBarrierExample() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        val (t1, t2, t3) = createRefs()
        val barrierEnd = createEndBarrier(t1, t2)
        Text(text = "First text content",
            modifier = Modifier.constrainAs(t1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)

            })
        Text(text = "Second text content ",
            modifier = Modifier.constrainAs(t2) {
                start.linkTo(parent.start)
                top.linkTo(t1.bottom)
            })
        Text(text = "Third text content",
            modifier = Modifier.constrainAs(t3) {
                start.linkTo(barrierEnd)
                top.linkTo(t1.bottom)
            })
    }
}


//Chain in Constraint
@Composable
fun CreateChainsExample() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        val (textOne, textTwo, textThree) = createRefs()
createHorizontalChain(textOne,textTwo,textThree)
        Text(text = "Text One", modifier = Modifier.constrainAs(textOne) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)

        })
        Text(text = "Text Two", modifier = Modifier.constrainAs(textTwo) {
            start.linkTo(textOne.end, margin = 8.dp)
            top.linkTo(textOne.top)
            bottom.linkTo(textOne.bottom)
        })
        Text(text = "Text Three", modifier = Modifier.constrainAs(textThree) {
            start.linkTo(textTwo.end, margin = 8.dp)
            top.linkTo(textTwo.top)
            bottom.linkTo(textTwo.bottom)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ConstraintLayoutComposeApplicationTheme {
        CreateChainsExample()

    }
}