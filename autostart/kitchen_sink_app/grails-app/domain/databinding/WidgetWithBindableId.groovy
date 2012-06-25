package databinding

class WidgetWithBindableId {
	String name
	
	static constraints = {
		id bindable: true
	}
}
