import java.util.ArrayList;
import java.util.List;


public class Status {

	boolean flag;
	List<String> reasons;
	
	
	public Status() {
		super();
		this.flag = true;
		this.reasons = new ArrayList<>();
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<String> getReasons() {
		return reasons;
	}
	public void setReasons(String reason) {
		List<String> s =  this.getReasons();
		s.add(reason);
		this.reasons = s;
	}
	
	
}
