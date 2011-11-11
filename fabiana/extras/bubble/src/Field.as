package  
{
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.text.TextField;
	import flash.text.TextFormat;
	/**
	 * input/output textfield
	 * @author thi
	 */
	public class Field extends Sprite
	{
		public var flag:Boolean;
		public var time:int = 0;
		public var timeOut:int = 30;
		
		public var tf:TextField;
		private var g:Graphics;
		
		public function Field() 
		{
			var f:TextFormat = new TextFormat("Arial", 14);
			tf = new TextField();
			tf.defaultTextFormat = f;
			tf.type = "input";
			//tf.text = "9991232387418521";
			tf.text = "12";
			tf.textColor = 0xFFFFFF;
			tf.height = 20;
			tf.multiline = false;
			this.addChild(tf);
			
			// debug
			tf.border = true;
		}
		
		public function resize(W:Number, H:Number):void
		{
			// text
			tf.width = W;
			
			// background
			g = this.graphics;
			g.beginFill(0, .8);
			g.drawRect(0, 0, W, 20);
			g.endFill();
			g = null;
		}
		
	}

}