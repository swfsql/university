package 
{
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	
	/**
	 * ...
	 * @author Thi
	 */
	public class Main extends Sprite 
	{
		
		private var W:Number = 0, 
					H:Number = 0,
					radius:Number = Math.PI * .75, // radius from an sphere's volume input
					rads:Number = Math.PI / 180; // degress to rads
		
		public function Main():void 
		{
			// setup
				stage.scaleMode = StageScaleMode.NO_SCALE;
				stage.align = StageAlign.TOP_LEFT;
				stage.frameRate = 40;
			// event
				this.addEventListener(Event.ENTER_FRAME, ef);
				stage.addEventListener(Event.RESIZE, resize);
		}
		
		private function ef(e:Event):void 
		{
			
		}
		
		private function resize(e:Event = null):void 
		{
			
			// app dimension
				W = stage.stageWidth;
				H = stage.stageHeight;
		}
		
	}
	
}