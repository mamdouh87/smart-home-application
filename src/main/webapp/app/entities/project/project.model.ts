import dayjs from 'dayjs/esm';
import { IProjectTemplate } from 'app/entities/project-template/project-template.model';
import { IBuildingType } from 'app/entities/building-type/building-type.model';
import { ISubProject } from 'app/entities/sub-project/sub-project.model';

export interface IProject {
  id?: number;
  clientName?: string | null;
  enterDate?: dayjs.Dayjs | null;
  location?: string | null;
  projectTemplate?: IProjectTemplate | null;
  buildingType?: IBuildingType | null;
  subProjects?: ISubProject[] | null;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public clientName?: string | null,
    public enterDate?: dayjs.Dayjs | null,
    public location?: string | null,
    public projectTemplate?: IProjectTemplate | null,
    public buildingType?: IBuildingType | null,
    public subProjects?: ISubProject[] | null
  ) {}
}

export function getProjectIdentifier(project: IProject): number | undefined {
  return project.id;
}
