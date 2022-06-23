import { IRequirementItem } from 'app/entities/requirement-item/requirement-item.model';
import { ISubProject } from 'app/entities/sub-project/sub-project.model';

export interface IProjectItemsRequirement {
  id?: number;
  qtyNo?: number | null;
  notes?: string | null;
  requirementItems?: IRequirementItem | null;
  subProject?: ISubProject | null;
}

export class ProjectItemsRequirement implements IProjectItemsRequirement {
  constructor(
    public id?: number,
    public qtyNo?: number | null,
    public notes?: string | null,
    public requirementItems?: IRequirementItem | null,
    public subProject?: ISubProject | null
  ) {}
}

export function getProjectItemsRequirementIdentifier(projectItemsRequirement: IProjectItemsRequirement): number | undefined {
  return projectItemsRequirement.id;
}
