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
	private int _l; // length

	public StringUnit(String s) {
		this.value = s;
		next = null;
	}

	public String[] array () {
		int i = -1;
		StringUnit su = this;
		String[] s = new String[length()];
		while(++i < _l) {
			su = su.next;
			s[i] = su.value;
		}
		return s;
	}

	public int length () {
		_l = 0;
		StringUnit su = this;
		while((su = su.next) != null) ++_l; // length
		return _l;
	}
}