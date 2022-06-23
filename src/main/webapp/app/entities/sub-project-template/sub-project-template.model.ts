import { ISubProjectAttrTemplate } from 'app/entities/sub-project-attr-template/sub-project-attr-template.model';
import { IRequirementItem } from 'app/entities/requirement-item/requirement-item.model';
import { IProjectTemplate } from 'app/entities/project-template/project-template.model';

export interface ISubProjectTemplate {
  id?: number;
  projectTemplateCode?: string | null;
  projectTemplateNameAr?: string | null;
  projectTemplateNameEn?: string | null;
  subProjectAttrTemplates?: ISubProjectAttrTemplate[] | null;
  requirementItems?: IRequirementItem[] | null;
  projectTemplate?: IProjectTemplate | null;
}

export class SubProjectTemplate implements ISubProjectTemplate {
  constructor(
    public id?: number,
    public projectTemplateCode?: string | null,
    public projectTemplateNameAr?: string | null,
    public projectTemplateNameEn?: string | null,
    public subProjectAttrTemplates?: ISubProjectAttrTemplate[] | null,
    public requirementItems?: IRequirementItem[] | null,
    public projectTemplate?: IProjectTemplate | null
  ) {}
}

export function getSubProjectTemplateIdentifier(subProjectTemplate: ISubProjectTemplate): number | undefined {
  return subProjectTemplate.id;
}
