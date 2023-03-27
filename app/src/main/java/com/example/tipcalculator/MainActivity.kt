package com.example.tipcalculator

import java.text.NumberFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var billAmountInput by remember { mutableStateOf("") }
    val amount = billAmountInput.toDoubleOrNull() ?: 0.0
    var tipPercent by remember { mutableStateOf(0)}
    val tip = calculateTip(amount, tipPercent)
    val total = calculateTotal(amount, tip)

    val tipArray = intArrayOf(10, 15, 18, 25)
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                billAmountInput = String.format("%.2f", Random.nextDouble(0.0, 100.0))
                tipPercent = tipArray[Random.nextInt(0, 4)]
            },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(color = Color.Black, width = 1.dp),
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            )
        ) {
            Text(
                text = "Generate Random Bill",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(Modifier.height(24.dp))

        EditServiceCostField(
            value = billAmountInput,
            onValueChange = { billAmountInput = it }
        )
        EditTipPercentageField(
            onValueChange = { tipPercent = it }
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.bill_amount_display, "$$billAmountInput"),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.tip_percentage, "$tipPercent%"),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.tip_amount, "$$tip"),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.total_amount, total),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }
}


@Composable
fun EditTipPercentageField(
    onValueChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            Button(
                modifier = Modifier.padding(end = 10.dp),
                onClick = { onValueChange(10) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(color = Color.Black, width = 1.dp),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                )

            ) {
                Text(text = "10%")
            }
            Button(
                modifier = Modifier.padding(start = 10.dp),
                onClick = { onValueChange(15) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(color = Color.Black, width = 1.dp),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                )

            ) {
                Text(text = "15%")
            }
        }
        Row(modifier = Modifier) {
            Button(
                modifier = Modifier.padding(end = 10.dp),
                onClick = { onValueChange(18) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(color = Color.Black, width = 1.dp),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                )

            ) {
                Text(text = "18%")
            }
            Button(
                modifier = Modifier.padding(start = 10.dp),
                onClick = { onValueChange(25) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(color = Color.Black, width = 1.dp),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                )

            ) {
                Text(text = "25%")
            }
        }
    }

}

@Composable
fun EditServiceCostField(
    value: String,
    onValueChange: (String) -> Unit
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.bill_amount)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private fun calculateTip(
    amount: Double,
    tipPercent: Int
): Double {
    return String.format("%.2f", tipPercent.toDouble() / 100 * amount).toDouble()
}

private fun calculateTotal(
    amount: Double,
    tip: Double
): String {
    val total = amount + tip
    return NumberFormat.getCurrencyInstance().format(total)
}

@Preview(
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}