package com.example.miappbasica.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.miappbasica.R

// La data class no cambia
data class Comic(
    val id: Int,
    val titulo: String,
    val autor: String,
    val sinopsis: String
)

/**
 * Esta es nuestra nueva fuente de datos.
 * Es un Composable que devuelve la lista de cómics. Al ser Composable,
 * puede usar `stringResource()` y se recompondrá automáticamente
 * cuando el idioma cambie, devolviendo los textos traducidos.
 */
@Composable
fun getMisComics(): List<Comic> {
    return listOf(
        Comic(
            id = 1,
            titulo = stringResource(id = R.string.collection_watchmen_titulo),
            autor = stringResource(id = R.string.collection_watchmen_autor),
            sinopsis = stringResource(id = R.string.collection_watchmen_desc)
        ),
        Comic(
            id = 2,
            titulo = stringResource(id = R.string.collection_dkr_titulo),
            autor = stringResource(id = R.string.collection_dkr_autor),
            sinopsis = stringResource(id = R.string.collection_dkr_desc)
        ),
        Comic(
            id = 3,
            titulo = stringResource(id = R.string.collection_maus_titulo),
            autor = stringResource(id = R.string.collection_maus_autor),
            sinopsis = stringResource(id = R.string.collection_maus_desc)
        ),
        Comic(
            id = 4,
            titulo = stringResource(id = R.string.collection_saga_titulo),
            autor = stringResource(id = R.string.collection_saga_autor),
            sinopsis = stringResource(id = R.string.collection_saga_desc)
        ),
        Comic(
            id = 5,
            titulo = stringResource(id = R.string.collection_walkingdead_titulo),
            autor = stringResource(id = R.string.collection_walkingdead_autor),
            sinopsis = stringResource(id = R.string.collection_walkingdead_desc)
        ),
        Comic(
            id = 6,
            titulo = stringResource(id = R.string.collection_walkingdead_titulo),
            autor = stringResource(id = R.string.collection_walkingdead_autor),
            sinopsis = stringResource(id = R.string.collection_walkingdead_desc)
        )
        // ...puedes añadir más cómics aquí
    )
}
