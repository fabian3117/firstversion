package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.models.ModelBeneficios
import com.example.myapplication.models.ModelCategoryBeneficios
import com.example.myapplication.models.Sedes
import com.example.myapplication.network.ApiService
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class ModeloVistas:ViewModel(){
    var nombre= mutableStateOf("nombre");
}
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = RetrofitInstance.getInstance()
        val apiService = retrofit.create(ApiService::class.java)

        val viewModel=ModeloVistas();
        lifecycleScope.launch {
            try {
                val response = apiService.getDataUser()
                if (response.isSuccessful) {
                    val userData = response.body()
                    Log.d("TAG", "Datos recibidos: $userData")

                    if (userData != null) {
                        viewModel.nombre= mutableStateOf(userData.nombre)
                    };
                    // Haz algo con los datos recibidos
                } else {
                    Log.e("TAG", "Error en la respuesta: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error en la llamada: ${e.message}")
            }
        }



        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Menu(viewModel)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun Menu(viewModel: ModeloVistas=ModeloVistas()) {
    val snackbarHostState = remember { SnackbarHostState() }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val nombre by viewModel.nombre;

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { /* Acción del ícono de navegación */ }) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Menu Icon"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Acción del ícono de búsqueda */ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    }
                },
                title = {
//                    Text("Bienvenido", textAlign = TextAlign.Center,)

                    CustomH2Text(text = "Bienvenido $nombre")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                /* Acción del botón flotante */
//TODO poner modalBottomSheet
                showBottomSheet = true;
            }) {
//                Text(text = "Nueva tarea")
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Nueva tarea",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Nueva tarea", color = Color.White)
                }
            }
        }, bottomBar = {
            barraInferior()
        },
        content = { innerPadding ->
            contentMain(modifier = Modifier.padding(innerPadding), clik = {showBottomSheet = true;Log.e("MIRA","EAS")});
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxSize(),
                    sheetState = sheetState, onDismissRequest = { showBottomSheet = false }) {
                    barraMateriaVistaInferior();

                }
            }
        }
    )
}
@Composable
fun contentMain(modifier: Modifier, clik: () -> Unit) {
    var materias = listOf("a","B");
/*
    Column(
        modifier = modifier
    ) {
        LazyColumn {
            items(materias) { materia ->

                Text(text = materia,);
            }
        }
    }

 */
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(modifier = modifier) {
            CardUser();
            cardLastSede();
            rutinas();
            beneficios()
        }

    }

}
@Preview
@Composable
fun CardUser(){
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
    TextoDoradoGrande("Tu targeta de socio");
    Card(
        modifier = Modifier.fillMaxWidth(1f).border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
        contentColor = MaterialTheme.colorScheme.onBackground,
        elevation = 4.dp,
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Plan", color = MaterialTheme.colorScheme.background)
Row {
    Text(text = "Apto medico valido : 18/09/2025", color = MaterialTheme.colorScheme.background)
    Icon(imageVector = Icons.Filled.Check, contentDescription = "Validez de apto medico");
}
            Text(text = "Vencimiento : 12/09/2025", color = MaterialTheme.colorScheme.background)
            Text(text ="Codigo de socio : 555948945564", color = MaterialTheme.colorScheme.background )

        }
    }
    }
}
@Preview
@Composable
fun beneficios(){
    val beneficios= listOf(
        ModelCategoryBeneficios(categoria = "Gastronomia", icono = "LL", url = "ll"),
        ModelCategoryBeneficios(categoria = "Compras", icono = "LL", url = "ll"),
    );
Column (verticalArrangement = Arrangement.SpaceBetween) {
    TextoDoradoGrande("Beneficios para vos")
    Text(text = "Mas cercanos")
    Column(modifier = Modifier)  {
        LazyColumn { items(beneficios){
                item->
            CardBeneficios(item)
        } }

    }
}

}
@Preview
@Preview
@Composable
fun CardBeneficios(categoria: ModelCategoryBeneficios = ModelCategoryBeneficios("A", "B", "c")) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card (modifier = Modifier
            .size(50.dp),
            backgroundColor = MaterialTheme.colorScheme.onBackground){  }
        Card(modifier = Modifier
            .fillMaxSize(1f).size(50.dp).padding(start = 10.dp, end = 10.dp)) {
            Column(modifier = Modifier.padding(start = 10.dp, top = 10.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = categoria.categoria, color = MaterialTheme.colorScheme.background)
            }
        }
    }
}

@Composable
fun CustomH2Text(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 24.sp, // Tamaño grande similar a H2
            fontWeight = FontWeight.Bold, // Peso del texto en negrita
            color = MaterialTheme.colorScheme.onBackground, // Color del texto acorde al tema
            lineHeight = 32.sp // Espaciado entre líneas para mejorar la legibilidad
        ),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp) // Padding para separar el texto del borde
    )
}
@Preview
@Composable
fun cardLastSede(){
val title="Tus Ultimas sedes";
    val activityes=listOf("A","B","C");

    val sedes=listOf(Sedes("nombreA","IMG",activityes));
    Column(modifier = Modifier)  {
        //CustomH2Text(title);
        TextoDoradoGrande(title);
        LazyColumn { items(sedes){
            item->
            CustomerCard(item);
        } }

    }
}
@Preview
@Composable
fun rutinas(){
    val dias= listOf("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado");

    TextoDoradoGrande("Rutina");
    ChipsHorizontal(dias);
}
@Composable
fun TextoDoradoGrande(text: String="MENSAJE") {
    Text(
        text = text,
        style = TextStyle(
            color = Color(0xFFD4AF37),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(16.dp))
}
@Preview
@Composable
fun CustomerCard(sede:Sedes=Sedes("Sede Nombre","B", arrayListOf("Musculacion","Crossfit","Natacion"))){
val horaIngreso:String="12:00";
    Card(
        modifier = Modifier.fillMaxWidth(1f).border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = sede.nameSede, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Hora ingreso : ${horaIngreso}")
            Spacer(modifier = Modifier.height(16.dp))
            ChipsHorizontal(sede.activitys)

        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ChipsHorizontal(listado: List<String> =arrayListOf("A","B","C")){
//--> Dorado en el borde
    LazyRow(horizontalArrangement = Arrangement.spacedBy(3.dp)) { items(listado){
        item: String ->
        Chip(onClick = {}, colors = ChipDefaults.chipColors(
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            leadingIconContentColor = MaterialTheme.colorScheme.primary,
        ), border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFD4AF37)
        )) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = item,color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.background(MaterialTheme.colorScheme.background))
        }
    } }
}
//@Preview
@Composable
//@Composable
fun barraMateriaVistaInferior(
    nombreMateria: String = "Fisica 1",
    temario: List<String> = listOf(
        "Cinemática",
        "Dinámica",
        "Trabajo y Energía",
        "Momentum (Cantidad de Movimiento)",
        "Gravitación",
        "Oscilaciones y Ondas",
        "Fluidos",
        "Termodinámica"
    )
) {
    Box(modifier = Modifier.padding(10.dp)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp)) {
                CustomH2Text(text = nombreMateria)
                LazyColumn {
                    items(temario) { tem ->
                        Text(text = tem)
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp), onClick = { /*TODO*/ }) {
                    Text(text = "Ver apuntes")
                }
            }
        }
    }
}

@Composable
fun barraInferior() {
    BottomAppBar(
        containerColor = Color.Transparent, // Color de fondo del BottomAppBar
        tonalElevation = 0.dp, modifier = Modifier.padding(10.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f, true))
        BottomBarItem(
            icon = Icons.Default.Home,
            label = "Inicio",
            onClick = { /* Acción de Inicio */ }
        )
        BottomBarItem(
            icon = Icons.Default.LocationOn,
            label = "Sedes",
            onClick = { /* Acción de Buscar */ }
        )
        BottomBarItem(
            icon = Icons.Default.Info,
            label = "Qr",
            onClick = { /* Acción de Perfil */ }
        )
        BottomBarItem(
            icon = Icons.Default.FavoriteBorder,
            label = "Beneficios",
            onClick = { /* Acción de Perfil */ }
        )
        BottomBarItem(
            icon = Icons.Default.AccountBox,
            label = "Rutina",
            onClick = { /* Acción de Perfil */ }
        )
        BottomBarItem(
            icon = Icons.Default.AccountCircle,
            label = "Perfil",
            onClick = { /* Acción de Perfil */ }
        )
        Spacer(modifier = Modifier.weight(1f, true))
    }
}
@Composable
fun BottomBarItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White
            )
        }
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun barraNavegacion(){

    BadgedBox(
        badge = {
            Badge {
                val badgeNumber = "8"
                Text(
                    badgeNumber,
                    modifier =
                    Modifier.semantics {
                        contentDescription = "new notifications"
                    }
                )
            } }
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Distribuye elementos con espacio entre ellos
            verticalAlignment = Alignment.CenterVertically // Alinea verticalmente los elementos
        ){
            Icon(Icons.Filled.Star, contentDescription = "Favorite")
            Icon(Icons.Filled.Favorite, contentDescription = "Favorite")

        }
    };
}
//@Preview(showBackground = true)
@Composable
fun BottomNavBar() {
    val selectedIndex = remember { mutableStateOf(0) }

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Profile") },
            label = { Text(text = "Inicio") },
            selected = selectedIndex.value == 0,
            onClick = { selectedIndex.value = 0 }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Star, contentDescription = "Profile") },
            label = { Text(text = "Sedes") },
            selected = selectedIndex.value == 1,
            onClick = { selectedIndex.value = 1 }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Create, contentDescription = "Profile") },
            label = { Text(text = "Qr") },
            selected = selectedIndex.value == 2,
            onClick = { selectedIndex.value = 2 }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Check, contentDescription = "Profile") },
            label = { Text(text = "Beneficios") },
            selected = selectedIndex.value == 3,
            onClick = { selectedIndex.value = 3 }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text(text = "Perfil") },
            selected = selectedIndex.value == 4,
            onClick = { selectedIndex.value = 4 }
        )
    }
}