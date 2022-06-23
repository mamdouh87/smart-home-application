import { IRequirementItem } from 'app/entities/requirement-item/requirement-item.model';

export interface IHardwareItems {
  id?: number;
  hardwareDescAr?: string | null;
  hardwareDescEn?: string | null;
  supportedQty?: number | null;
  item?: IRequirementItem | null;
}

export class HardwareItems implements IHardwareItems {
  constructor(
    public id?: number,
    public hardwareDescAr?: string | null,
    public hardwareDescEn?: string | null,
    public supportedQty?: number | null,
    public item?: IRequirementItem | null
  ) {}
}

export function getHardwareItemsIdentifier(hardwareItems: IHardwareItems): number | undefined {
  return hardwareItems.id;
}
