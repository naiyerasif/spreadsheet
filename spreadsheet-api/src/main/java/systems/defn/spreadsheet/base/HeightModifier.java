package systems.defn.spreadsheet.base;

import systems.defn.spreadsheet.builder.api.CellDefinition;
import systems.defn.spreadsheet.builder.api.DimensionModifier;

public class HeightModifier implements DimensionModifier {

  private double pointsPerCentimeter;
  private double pointsPerInch;
  private final CellDefinition cell;
  private final double height;

  // FIXME: there's a potential risk in this API as calling e.g. .height(12).cm().cm() would power the width twice
  public HeightModifier(CellDefinition cell, double height, double pointsPerCentimeter, double pointsPerInch) {
    this.height = height;
    this.cell = cell;
    this.pointsPerCentimeter = pointsPerCentimeter;
    this.pointsPerInch = pointsPerInch;
  }

  @Override
  public CellDefinition cm() {
    cell.height(pointsPerCentimeter * height);
    return cell;
  }

  @Override
  public CellDefinition inch() {
    cell.height(pointsPerInch * height);
    return cell;
  }

  @Override
  public CellDefinition inches() {
    return inch();
  }

  @Override
  public CellDefinition points() {
    return cell;
  }
}
