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
				ground.back.addChild(backBubbles.bubbles);
				this.addChild(bubbles.bubbles); // bubbles
				this.addChild(text); // input
				this.addChild(text2); // result
				this.addChild(ground.front); // foreground
				ground.front.addChild(frontBubbles.bubbles);
			
			// event
				this.addEventListener(Event.ENTER_FRAME, ef);
				stage.addEventListener(Event.RESIZE, resize);
			
			// var
				s = text.tf.text;
			
			// init
				resize();
				// bubbles
				bubbles.init(s, - radius);
				bubbles.bubbles.y = 20; // user's bubbles
				backBubbles.init("123123", radius);
				backBubbles.bubbles.y = 20;
				// text
				stage.focus = text.tf;
				text.tf.setSelection(0, text.tf.text.length);
				text.x = text.y = text2.x = 0; // text
		}
		
		// 
		private var ground:Ground = new Ground(), // background, foreground
					//
					bubbles:Bubbles = new Bubbles(), // bubble's list's list
					backBubbles:Bubbles = new Bubbles(),
					frontBubbles:Bubbles = new Bubbles(),
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
				b = backBubbles;
				while (b = b.next)
					b.bubbles.x = W / 2;
				b = frontBubbles;
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
			// move & sort user's bubbles
			text2.tf.text = b.sort(); // user's
			var zoom:Number = b.zoom(W, H); // adjust the bubbles zooming
			
			// move & sort background bubbles
			var dx:Number = Math.random() - .5, dy:Number = -10;
			var s2:String;
			b = backBubbles;
			b.sort();
			b.apllyZoom(zoom);
			if (!b.move(W, H, dx, dy))
			{
				s2 = "123123";
				b.replace(W, H, s2);
				trace("vorts");
			}
		}
		
	}
	
}