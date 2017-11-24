package a.b.c.infra.tabledata;

import java.util.LinkedHashMap;
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

	public void setCellValue(String columnName, String value) {
		data.put(columnName, value);
	}

}
