package 
{
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	
	/**
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
				this.addChild(gr.back); // background
				this.addChild(bubbles.bubbles); // bubbles
				this.addChild(text); // input
				this.addChild(text2); // result
				this.addChild(gr.front); // foreground
			
			// event
				this.addEventListener(Event.ENTER_FRAME, ef);
				stage.addEventListener(Event.RESIZE, resize);
			
			// var
				s = text.tf.text;
			
			// init
				resize();
				bubbles.init(s, radius);
				text.x = text.y = text2.x = 0; // text
				bubbles.bubbles.y = 20; // user's bubbles
		}
		
		// 
		private var ground:Ground = new Ground(), // background, foreground
					//
					bubbles:Bubbles = new Bubbles(), // head, user's bubbles
					//
					s:String = "",
					s2:String = "",
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
				gr.resize();
			
			// bubbles
				var b:Bubbles = bubbles;
				b.bubbles.x = W / 2;
				while (b = b.next)
					b.bubbles.x = W / 2;
		}
		
		private function ef(e:Event):void 
		{
			if (s != text.tf.text)
				if (++text.time > text.timeOut)
				{
					text.time = 0;
					s = text.tf.text;
					bubblesInit();
				}
			var s2:String = "";
			
			var b:Bubble = bubbles;
			var b2:Bubble; // pra segurar a última bolha
			var next:Bubble;
			while (b = b.next)
			{
				s2 += String(b.info);
				next = b.next;
				b.vel = 0;
				if (next)
				{
					//if (b.info > next.info && (!next.passing || !next.next))
					if (b.info > next.info)
					{
						b.passing = true;
						// relação dos raios, sendo que a .info é o volume
						{
							// volume = 4/3 pi r² ... r = sqrt (3/ 4pi volume)
							var rel:Number = b.radius / next.radius;
							b.teta += (1 / rel) * .04 + b.teta*.2;
						}
						
						if (next.passing && next.next)
						{
							b.teta *= 0.8;
						}
						
						if (b.teta > Math.PI)
						{
							next.prev = b.prev;
							b.prev = next;
							b.next = next.next;
							next.next = b;
							
							if (b.next)
								b.next.prev = b;
							if (next.prev)
								next.prev.next = next;
						}
						
					} else
					{
						b.passing = false;
						b.teta *= .2;
					}
				}
				b2 = b;
			}
			text2.tf.text = s2;
			
			b = b2;
			var xn:Number;
			var yn:Number;
			
			b.x = 0;
			b.y = 0;
			
			while (b = b.prev)
			{
				if (b == bubbles)
					break;
				
				if (b.passing)
				{
					next = b.next;
					var r:Number = (next.radius + b.radius) / 2;
					var xc:Number = (b.x + next.x) / 2;
					var yc:Number = (b.y + next.y) / 2;
					b.x = xc + Math.sin(b.teta) * r;
					b.y = yc + Math.cos(b.teta) * r;
					
					if (!next.next)
					{
						next.x += (xc - Math.sin(b.teta) * r - next.x) * .1;
						next.y += (yc - Math.cos(b.teta) * r - next.y) * .1;
						
					} else
					{
						next.x = xc - Math.sin(b.teta) * r;
						next.y = yc - Math.cos(b.teta) * r;
						
					}
					
					
					
					continue;
				}
				
				{
					next = b.next;
					b.x += (next.x - b.x ) * .7;
					b.y += (next.y + next.radius + b.radius - b.y ) * .7;
					
					b.x += Math.random() * 2 - 4;
					
				}	
			}
			
			var min:Number = Math.min(W / bubbles.width, (H - 100) / bubbles.height);
			bubbles.scaleY += (min - bubbles.scaleY) * .2;
			bubbles.scaleX = bubbles.scaleY;
			
		}
		
	}
	
}