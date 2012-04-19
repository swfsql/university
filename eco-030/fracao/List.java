public class List {

	private StringUnit _head, _unit;
	
	public List() {
		clear();	
	}

	public void clear() {
		_head = new StringUnit("0");
		_unit = _head;
	}

	public void add (String s) {
		_unit.next = new StringUnit(s);
		_unit = _unit.next;
	}

	public StringUnit head() {
		return _head;
	}
}

class StringUnit {
	public StringUnit next;
	public String value;

	public StringUnit(String s) {
		this.value = s;
		next = null;
	}
}