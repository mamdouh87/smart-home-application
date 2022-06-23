export interface IBuildingType {
  id?: number;
  code?: string | null;
  nameAr?: string | null;
  nameEn?: string | null;
}

export class BuildingType implements IBuildingType {
  constructor(public id?: number, public code?: string | null, public nameAr?: string | null, public nameEn?: string | null) {}
}

export function getBuildingTypeIdentifier(buildingType: IBuildingType): number | undefined {
  return buildingType.id;
}
