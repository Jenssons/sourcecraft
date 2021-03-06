package vmfWriter.entity.pointEntity.pointEntity;

import java.io.IOException;

import minecraft.Position;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public class InfoPlayerT extends RotateablePointEntity {

	@Override
	public InfoPlayerT create(Position origin) {
		InfoPlayerT result = new InfoPlayerT();
		result.setRotation(this.getRotation());
		result.setOrigin(origin);
		return result;
	}

	@Override
	public String getName() {
		return "info_player_terrorist";
	}

	@Override
	public void writeVmfSpecific(ValveWriter writer) throws IOException {
		writer.put("angles", "0 " + this.getRotation() + " 0");
	}
}
