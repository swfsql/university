package  
{
	import flash.display.BlendMode;
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.filters.GlowFilter;
	/**
	 * blub.
	 * TODO: make it pretty.
	 * TODO: generalize
	 * @author thi
	 */
	public class Bubble extends Sprite
	{
		public var next:Bubble,
				   prev:Bubble,
				   //
				   info:Number, // the sphere's volume
				   radius:Number,
				   teta:Number = 0, // the movement
				   passing:Boolean; // if going up
		
		public function Bubble() 
		{
			
		}
		
		// vector without bmData's cpyPixel
		public function draw(info:Number, rm:Number, ro:Number):Bubble
		{
			// information
			this.passing = false;
			this.info = info;
			radius = rm * info * ro;
			
			// draw it
			var g:Graphics = this.graphics;
			g.clear();
			//g.lineStyle(.5 , 0xFFFFFF, .1);
			g.beginFill(0x707090, .4);
			g.drawCircle(0, 0, radius);
			g.endFill();
			g = null;
			
			return this;
		}
		
		public function clear():Bubble
		{
			this.graphics.clear();
			return this;
		}
		
		public function stop():void
		{
			// draw it
			var g:Graphics = this.graphics;
			g.clear();
			g.beginFill(0x709090, .4);
			g.drawCircle(0, 0, radius);
			g.endFill();
			g = null;
			
		}
		
		public function comparing():void
		{
			// draw it
			var g:Graphics = this.graphics;
			g.clear();
			g.beginFill(0x103030, .4);
			g.drawCircle(0, 0, radius);
			g.endFill();
			g = null;
		}
		
		public function normal():void
		{
			// draw it
			var g:Graphics = this.graphics;
			g.clear();
			g.beginFill(0x707090, .4);
			g.drawCircle(0, 0, radius);
			g.endFill();
			g = null;
		}
		
	}
}