package 
{
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	
	/**
	 * bubble sorting visualization.
	 * TODO: environment blubbles
	 * @author thi
	 */
	public class Main extends Sprite 
	{
		public function Main():void 
		{
			// setup
				stage.scaleMode = StageScaleMode.NO_SCALE;
				stage.align = StageAlign.TOP_LEFT;
				stage.frameRate = 40;
			
			// children
				this.addChild(ground.back); // background
				this.addChild(bubbles.bubbles); // bubbles
				this.addChild(text); // input
				this.addChild(text2); // result
				this.addChild(ground.front); // foreground
			
			// event
				this.addEventListener(Event.ENTER_FRAME, ef);
				stage.addEventListener(Event.RESIZE, resize);
			
			// var
				s = text.tf.text;
			
			// init
				resize();
				bubbles.init(s,- radius);
				text.x = text.y = text2.x = 0; // text
				bubbles.bubbles.y = 20; // user's bubbles
				stage.focus = text.tf;
				text.tf.setSelection(0, text.tf.text.length);
		}
		
		// 
		private var ground:Ground = new Ground(), // background, foreground
					//
					bubbles:Bubbles = new Bubbles(), // bubble's list's list
					//
					s:String = "",
					text:Field = new Field(), // input text
					text2:Field = new Field(), // sorted text
					//
					W:Number = 0, 
					H:Number = 0,
					radius:Number = Math.PI * .75; // radius from an sphere's volume input
		
		private function resize(e:Event = null):void 
		{
			
			// app dimension
				W = stage.stageWidth;
				H = stage.stageHeight;
			
			// texts
				text.resize(W, H);
				text2.resize(W, H);
				text2.y = H - 20;
			
			// ground
				ground.resize(W,H);
			
			// bubbles
				var b:Bubbles = bubbles;
				b.bubbles.x = W / 2;
				while (b = b.next)
					b.bubbles.x = W / 2;
		}
		
		private function ef(e:Event):void 
		{
			// re init user's bubbles
			if (s != text.tf.text && ++text.time > text.timeOut)
			{
				text.time = 0;
				s = text.tf.text;
				if (s == "") s = "1";
				bubbles.init(s, radius);
			}
			
			var b:Bubbles = bubbles;
			// move & sort bubbles
			text2.tf.text = b.sort(); // user's
			b.zoom(W, H); // adjust the bubbles zooming
		}
		
	}
	
}