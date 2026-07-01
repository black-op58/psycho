package com.sanin.tv.others.calc
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.sanin.tv.R
import com.sanin.tv.util.Logger
object BiometricPromptUtils {
    private const val TAG = "BiometricPromptUtils"    /**     * Create a BiometricPrompt instance     * @param activity: AppCompatActivity     * @param processSuccess: success callback     */    
fun createBiometricPrompt(        activity: AppCompatActivity,        processSuccess: (BiometricPrompt.AuthenticationResult) -> Unit    ): BiometricPrompt {
    val executor = ContextCompat.getMainExecutor(activity)        
val callback = 
object : BiometricPrompt.AuthenticationCallback() {
    override fun onAuthenticationError(errCode: Int, errString: CharSequence) {                
        s

override fun onAuthenticationFailed() {                
        s

override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {                
        s
            }
}
return BiometricPrompt(activity, executor, callback)    }
/**     * Create a BiometricPrompt.PromptInfo instance     * @param activity: AppCompatActivity     * @return BiometricPrompt.PromptInfo: instance     */
fun createPromptInfo(activity: AppCompatActivity): BiometricPrompt.PromptInfo =        BiometricPrompt.PromptInfo.Builder().apply {            
        s
            setConfirmationRequired(false)
            setNegativeButtonText(activity.getString(R.string.cancel))
        }.build()}