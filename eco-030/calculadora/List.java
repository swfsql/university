public class List {

	private StringUnit _head, _tail, _unit;
	
	public List() {
		clear();	
	}

	public void clear() {
		_head = new StringUnit("0");
		_tail = null;
		_unit = _head;
	}

	public void add (String s) {
		_unit.next = new StringUnit(s);
		if (_unit != _head) _unit.next.prev = _unit;
		_unit = _unit.next;
		_tail = _unit;
		System.out.print("add: "); System.out.println(s);
	}

	public StringUnit head() {
		return _head;
	}

	// iterator
	public StringUnit now = null;
	public void start() {
		now = _head;
	}
	public StringUnit next() {
		if (now != null) now = now.next;
		return now;
	}
	// reverse iterator
	public void end() {
		now = _tail;
	}
	public StringUnit prev() {
		if (now != null) now = now.prev;
		return now;
	}

	// iterator
	public void rmNext() {
		if(now == null || now.next == null) return;
		if(_tail == now.next) _tail = now;
		now.next = now.next.next;
		now.next.prev = now;
	}


}

class StringUnit {
	public StringUnit next, prev;
	public String value;

	public StringUnit(String s) {
		this.value = s;
		next = prev = null;
	}
}