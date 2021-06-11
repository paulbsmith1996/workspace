package inventories;

// Paul Baird-Smith 2015

// Credit where credit is due: Code originally written by Brent Yorgey
// Williams College CS136
public class Association<K, V> {

	// Holds a key and a value involved in association
	protected K key;
	protected V value;

	// Constructor using a key and value
	public Association(K key, V value) {
		// Check for null key
		assert key != null : "Null key";
		// Assign instance variables to parameter
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}
	
	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	// Set a new value for the association
	public V setValue(V newValue) {
		V old = value;
		value = newValue;
		return old;
	}
}