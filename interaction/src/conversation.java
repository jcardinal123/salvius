import com.google.code.chatterbotapi.*;
import com.sun.speech.freetts.VoiceManager;

/* FOR THE APIS USED REFER TO COPYRIGHT AND USAGE TEXT FILES */

public class conversation {
	
	public static String s = "default text";
	public static String input = "hello world";
	
	public static void main(String[] argv) throws Exception {
		
		// INITIALISE SPEECH SYNTHESIS AND RECOGNITION
		voce.SpeechInterface.init("lib", true, true, "./lib/grammar", "test");
		ChatterBotFactory factory = new ChatterBotFactory();
		ChatterBot bot1 = factory.create(ChatterBotType.CLEVERBOT);
		ChatterBotSession bot1session = bot1.createSession();
		Voice voiceKevin16 = new Voice("kevin16");

		// SETS RECOGNITION ON OR OFF
		boolean quit = false;
		boolean confirm = false;
		boolean listen = false;
		
		while (!quit && !confirm) {
			
			while (!listen) {
				
				// Normally, applications would do application-specific things here
				// For this sample, we'll just sleep for a little bit
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e) {
				}
				
				// LISTEN FOR THE USER TO SAY SOMETHING
				while (voce.SpeechInterface.getRecognizerQueueSize() > 0) {
					s = voce.SpeechInterface.popRecognizedString();
	
					// QUIT SPEECH RECOGNITION IF INSTRUCTED
					if (-1 != s.indexOf("quit")) {
						quit = true;
					}
					
					if (-1 != s.indexOf("confirm")) {
						quit = true;
					}
	
					System.out.println("You said: " + s);
					//voce.SpeechInterface.synthesize(s);
					
					// STOP LISTENING AND SAY SOMETHING
					listen = true;
				}
			}
			
			while (listen) {
				
				// GENERATE A RESPONCE TO THE USERS INPUT
				System.out.println("bot1> " + input);
				voiceKevin16.say(input);
				input = bot1session.think(s);
				
				// STOP SPEAKING AND LISTEN
				listen = false;
			}
			
		}

		voce.SpeechInterface.destroy();
		System.exit(0);
	}
}

// VOICE CLASS DEFINES CHARACTERISTICS OF THE CHAT BOT'S VOICE
class Voice {
    private String name;
    private com.sun.speech.freetts.Voice systemVoice;

    public Voice(String name) {
        this.name = name;
        this.systemVoice = VoiceManager.getInstance().getVoice(this.name);
        this.systemVoice.allocate();
    }

    public void say(String[] thingsToSay) {
        for (int i = 0; i < thingsToSay.length; i++) {
            this.say(thingsToSay[i]);
        }
    }

    public void say(String thingToSay) {
        this.systemVoice.speak(thingToSay);
    }

    public void dispose() {
        this.systemVoice.deallocate();
    }
}