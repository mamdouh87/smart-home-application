import { ISubProject } from 'app/entities/sub-project/sub-project.model';

export interface ISubProjectAttr {
  id?: number;
  attrCode?: string | null;
  attrCodeNameAr?: string | null;
  attrCodeNameEn?: string | null;
  attrType?: string | null;
  attrValue?: string | null;
  subProject?: ISubProject | null;
}

export class SubProjectAttr implements ISubProjectAttr {
  constructor(
    public id?: number,
    public attrCode?: string | null,
    public attrCodeNameAr?: string | null,
    public attrCodeNameEn?: string | null,
    public attrType?: string | null,
    public attrValue?: string | null,
    public subProject?: ISubProject | null
  ) {}
}

export function getSubProjectAttrIdentifier(subProjectAttr: ISubProjectAttr): number | undefined {
  return subProjectAttr.id;
}
