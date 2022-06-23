import { ISubProjectTemplate } from 'app/entities/sub-project-template/sub-project-template.model';

export interface ISubProjectAttrTemplate {
  id?: number;
  attrCode?: string | null;
  attrCodeNameAr?: string | null;
  attrCodeNameEn?: string | null;
  attrType?: string | null;
  subProjectTemplate?: ISubProjectTemplate | null;
}

export class SubProjectAttrTemplate implements ISubProjectAttrTemplate {
  constructor(
    public id?: number,
    public attrCode?: string | null,
    public attrCodeNameAr?: string | null,
    public attrCodeNameEn?: string | null,
    public attrType?: string | null,
    public subProjectTemplate?: ISubProjectTemplate | null
  ) {}
}

export function getSubProjectAttrTemplateIdentifier(subProjectAttrTemplate: ISubProjectAttrTemplate): number | undefined {
  return subProjectAttrTemplate.id;
}
