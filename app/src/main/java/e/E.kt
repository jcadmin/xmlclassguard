package e

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class E: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
    }

    class NotifyJobService : JobService() {
        override fun onStartJob(params: JobParameters?): Boolean {
            return false
        }

        override fun onStopJob(params: JobParameters?): Boolean {
            return false
        }

    }
}