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
		private var gr:Ground; // desenho em volta
		private var bubbles:Bubble; // head
		
		
		private var text:Field; // campo de texto
		private var text2:Field;
		private var s:String;
		
		private var W:Number, H:Number;
		
		private const raio:Number = Math.PI * 3 / 4;
		
		
		
		public function Main():void 
		{
			stage.scaleMode = StageScaleMode.NO_SCALE;
			stage.align = StageAlign.TOP_LEFT;
			stage.addEventListener(Event.RESIZE, resize);
			
			// init
				bubbles = new Bubble();
				text = new Field();
				text2 = new Field();
				gr = new Ground();
				this.addChild(gr.back);
				this.addChild(bubbles);
				this.addChild(gr.front);
				this.addChild(text);
				this.addChild(text2);
			
			
			// event
				this.addEventListener(Event.ENTER_FRAME, ef);
			
			s = text.tf.text;
			resize();
			bubblesInit();
		}
		
		private function bubblesInit():void
		{
			var i:int = -1;
			var l:int = s.length;
			var b:Bubble = bubbles;
			var b2:Bubble = bubbles;
			
			while ((b = b.next) && ++i < l)
			{
				trace("i: ", i);
				b.info = Number (s.charAt(i));
				b.radius = raio * b.info;
				b.draw();
				b2 = b;
			}
			
			if (i == l)
			{
				trace("entrou aeee");
				b = b2;
				while (b2 = b2.next)
				{
					b2.graphics.clear();
					bubbles.removeChild(b2);
				}
				b.next = null;
				
			} else
			{
				while (++i < l)
				{
					b2.next = new Bubble();
					b2.next.prev = b2;
					b2 = b2.next;
					bubbles.addChild(b2);
					b2.info = Number (s.charAt(i));
					b2.radius = raio * b2.info;
					b2.draw();
					
				}
				
			}
			
			trace(bubbles, bubbles.next)
			
		}
		
		private function resize(e:Event = null):void 
		{
			
			// ajusta o tamanho da janela
				W = stage.stageWidth;
				H = stage.stageHeight;
			
			// ajusta o campo de texto
				text.resize(W, H);
				text.x = text.y = 0
				text2.resize(W, H);
				text2.x = 0;
				text2.y = H - 20;
			
			// ajusta o chão
				gr.resize();
			
			// ajusta todas as bolhas
				var b:Bubble = bubbles;
				while (b = b.next) 
					b.resize(W, H);
			
			bubbles.x = W / 2;
			bubbles.y = 60;
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