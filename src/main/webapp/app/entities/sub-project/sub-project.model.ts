import { ISubProjectAttr } from 'app/entities/sub-project-attr/sub-project-attr.model';
import { IProjectItemsRequirement } from 'app/entities/project-items-requirement/project-items-requirement.model';
import { IProject } from 'app/entities/project/project.model';

export interface ISubProject {
  id?: number;
  projectTemplateCode?: string | null;
  projectTemplateNameAr?: string | null;
  projectTemplateNameEn?: string | null;
  subProjectAttrs?: ISubProjectAttr[] | null;
  subProjectItemsReqs?: IProjectItemsRequirement[] | null;
  project?: IProject | null;
}

export class SubProject implements ISubProject {
  constructor(
    public id?: number,
    public projectTemplateCode?: string | null,
    public projectTemplateNameAr?: string | null,
    public projectTemplateNameEn?: string | null,
    public subProjectAttrs?: ISubProjectAttr[] | null,
    public subProjectItemsReqs?: IProjectItemsRequirement[] | null,
    public project?: IProject | null
  ) {}
}

export function getSubProjectIdentifier(subProject: ISubProject): number | undefined {
  return subProject.id;
}
