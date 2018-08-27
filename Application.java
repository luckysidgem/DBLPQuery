package dblp;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

//The application is organized in MVC pattern
public class Application {
	
	private static void  createAndShowGUI()	{
		ApplicationFrame f = new ApplicationFrame();
		f.startApp();
	}
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
		} 
		catch(Exception e)	{
			
		}
		SwingUtilities.invokeLater(()->
		{
			createAndShowGUI();
			
		});
	}

}
