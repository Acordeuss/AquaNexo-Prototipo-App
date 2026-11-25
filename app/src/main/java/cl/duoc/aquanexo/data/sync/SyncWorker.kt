package cl.duoc.aquanexo.data.sync

import android.content.Context
import androidx.work.*
import cl.duoc.aquanexo.data.local.AquaNexoDatabase
import cl.duoc.aquanexo.data.network.ApiClient
import cl.duoc.aquanexo.data.network.SyncRequest
import cl.duoc.aquanexo.model.EstadoSync
import cl.duoc.aquanexo.util.Reglas
import java.util.concurrent.TimeUnit

class SyncWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val dao = AquaNexoDatabase.obtener(applicationContext).dao()

        return try {
            dao.pendientesSync().forEach { registro ->
                val items = dao.obtenerItems(registro.id)
                val respuesta = ApiClient.api.sincronizar(
                    SyncRequest(registro, items)
                )

                dao.actualizarSync(
                    registro.id,
                    EstadoSync.SINCRONIZADO,
                    respuesta.id,
                    "${Reglas.fechaHoy()} ${Reglas.horaAhora()}"
                )
            }

            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }

    companion object {
        fun solicitar(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val work = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    15,
                    TimeUnit.SECONDS
                )
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    "aquanexo-sync",
                    ExistingWorkPolicy.REPLACE,
                    work
                )
        }
    }
}
