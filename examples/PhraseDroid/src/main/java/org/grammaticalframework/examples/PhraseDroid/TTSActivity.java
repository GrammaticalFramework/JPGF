package org.grammaticalframework.examples.PhraseDroid;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.content.Intent;
import java.util.Locale;

public abstract class TTSActivity extends Activity implements TextToSpeech.OnInitListener {
   private boolean tts_ready = false;
   private TextToSpeech mTts;
   static int MY_DATA_CHECK_CODE = 2347453;

   public abstract void onLanguageIsReady();
   public void setupLanguage() {
      Intent checkIntent = new Intent();
      checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
      startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
   }
   
   public void say(String txt) {
      this.mTts.speak(txt, TextToSpeech.QUEUE_ADD, null);
   }

   protected void onActivityResult(
           int requestCode, int resultCode, Intent data) {
       if (requestCode == MY_DATA_CHECK_CODE) {
           if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
               // success, create the TTS instance
               mTts = new TextToSpeech(this, this);
           } else {
               // missing data, install it
               Intent installIntent = new Intent();
               installIntent.setAction(
                   TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
               startActivity(installIntent);
           }
       }
   }
   
   // Implementing onInitListener
   public void onInit(int status) {
      if (status == TextToSpeech.SUCCESS) {
         mTts.setLanguage(Locale.FRANCE);
         this.tts_ready = true;
      }
      this.onLanguageIsReady();
   }
}
