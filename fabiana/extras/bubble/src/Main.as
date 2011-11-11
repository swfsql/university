package 
{
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	
	/**
	 * bubble sorting visualization.
	 * TODO: environment blubbles force map
	 * TODO: environment blubbles proper reposition on zooming/resizing
	 * TODO: full screen button
	 * @author thi
	 * inspired on http://wonderfl.net/c/8rbD
	 * for Universidade Federal de Itajubá - Campus Itabira
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
				// bubbles
				bubbles.init(s, radius);
				bubbles.bubbles.y = 20; // user's bubbles
				// text
				stage.focus = text.tf;
				text.tf.setSelection(0, text.tf.text.length);
				text.x = text.y = text2.x = 0; // text
				
			// ground bubbles
				groundBubblesInit();
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
					
					
		private function groundBubblesInit():void
		{
			// create background and foreground bubbles
			var i:int = -1, bs:Bubbles = backBubbles, s:String;
			while (++i < 5)
			{
				bs.next = new Bubbles();
				bs = bs.next;
				ground.back.addChild(bs.bubbles);
				s = String(int(Math.random() * Math.random() * 1000));
				bs.init(s, radius);
				bs.bubbles.y = 0;
				bs.bubbles.x = -20;
			}
			i = -1;
			bs = frontBubbles;
			while (++i < 5)
			{
				bs.next = new Bubbles();
				bs = bs.next;
				ground.front.addChild(bs.bubbles);
				s = String(int(Math.random() * Math.random() * 1000));
				bs.init(s, radius);
				bs.bubbles.y = 0;
				bs.bubbles.x = -20;
			}	
		}
		
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
				/*
				b = backBubbles;
				while (b = b.next)
				{
					b.bubbles.x = W / 2;
				}
				b = frontBubbles;
				while (b = b.next)
				{
					b.bubbles.x = W / 2;
				}
				*/
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
			
			var dx:Number, dy:Number, s2:String, offset:Number;
			// move & sort back/foreground bubbles
			b = backBubbles;
			while (b = b.next)
			{
				b.sort();
				b.apllyZoom(zoom);
				dx = Math.random() - .5;
				dy = -10;
				if (!b.move(W, H, dx, dy))
				{
					s2 = String(int(Math.random() * Math.random() * 1000));
					offset = Math.random();
					b.replace(W, H, s2, radius, offset);
				}
			}
			b = frontBubbles;
			while (b = b.next)
			{
				b.sort();
				b.apllyZoom(zoom);
				dx = Math.random() - .5;
				dy = -10;
				if (!b.move(W, H, dx, dy))
				{
					s2 = String(int(Math.random() * Math.random() * 1000));
					offset = Math.random() * Math.random() * 2;
					b.replace(W, H, s2, radius/8, offset*8);
				}
			}
			
			
		}
		
	}
	
}