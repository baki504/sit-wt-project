package a.b.c.infra.tabledata;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RowData {

	private Map<String, String> data = new LinkedHashMap<>();

	public RowData(String columnName, String value) {
		setCellValue(columnName, value);
	}

	public List<String> getColumnNames() {
		return new ArrayList<>(data.keySet());
	}

	public void setCellValue(String columnName, String value) {
		data.put(columnName, value);
	}

	public String getCellValue(String columnName) {
		return data.get(columnName);
	}

}
