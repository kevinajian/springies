package springies;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class inputListener implements KeyListener{

	public void checkForInput(KeyEvent e){
		// create new object
		if (getKey('n')){
			// let user enter
			// run that through parser
			// create new objects from that
			
		}
		if (getKey('c')){
			// remove all objects - clear
		}
		
		//List<Force> currentForces = WorldManager.getWorld().getForces();
		if (getKey('g')){
			//currentForces has "force", remove
			//else look up default/environment set force vec, addForces
			clearKey('g');	
		}
		if (getKey('v')){
			//currentForces has "force", remove
			//else look up default/environment set force vec, addForces
			clearKey('v');				
		}
		if (getKey('m')){
			//currentForces has "force", remove
			//else look up default/environment set force vec, addForces
			clearKey('m');			
		}
		if (getKey('1')){
			//currentForces has "force", remove
			//else look up default/environment set force vec, addForces
			clearKey('1');				
		}
		if (getKey('2')){
			//currentForces has "force", remove
			//else look up default/environment set force vec, addForces
			clearKey('2');				
		}
		if (getKey('3')){
			//currentForces has "force", remove
			//else look up default/environment set force vec, addForces
			clearKey('3');	
		}
		if (getKey('4')){
			//currentForces has "force", remove
			//else look up default/environment set force vec, addForces
			clearKey('4');				
		}
		
		// change the walls
		// have margins variable, store, add/subtract to it, remake walls?
		if (getKey(KeyDown)){
			clearKey(KeyDown);
		}
		if (getKey(KeyUp)){
			clearKey(KeyUp);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
