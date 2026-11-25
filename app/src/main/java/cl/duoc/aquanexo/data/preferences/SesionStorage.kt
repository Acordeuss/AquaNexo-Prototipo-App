package cl.duoc.aquanexo.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("aquanexo_sesion")

class SesionStorage(private val context: Context) {
    private val ultimoUsuario = stringPreferencesKey("ultimo_usuario")

    suspend fun guardar(usuario: String) {
        context.dataStore.edit { it[ultimoUsuario] = usuario }
    }

    suspend fun ultimo(): String =
        context.dataStore.data.first()[ultimoUsuario].orEmpty()

    suspend fun limpiar() {
        context.dataStore.edit { it.clear() }
    }
}
