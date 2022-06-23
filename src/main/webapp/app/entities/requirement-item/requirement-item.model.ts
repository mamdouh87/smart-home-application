import { ISubProjectTemplate } from 'app/entities/sub-project-template/sub-project-template.model';

export interface IRequirementItem {
  id?: number;
  sysCode?: string | null;
  code?: string | null;
  descriptionAr?: string | null;
  descriptionEn?: string | null;
  subProjectTemplate?: ISubProjectTemplate | null;
}

export class RequirementItem implements IRequirementItem {
  constructor(
    public id?: number,
    public sysCode?: string | null,
    public code?: string | null,
    public descriptionAr?: string | null,
    public descriptionEn?: string | null,
    public subProjectTemplate?: ISubProjectTemplate | null
  ) {}
}

export function getRequirementItemIdentifier(requirementItem: IRequirementItem): number | undefined {
  return requirementItem.id;
}
