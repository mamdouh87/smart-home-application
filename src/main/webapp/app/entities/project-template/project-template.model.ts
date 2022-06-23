import { ISubProjectTemplate } from 'app/entities/sub-project-template/sub-project-template.model';

export interface IProjectTemplate {
  id?: number;
  projectTemplateCode?: string | null;
  projectTemplateNameAr?: string | null;
  projectTemplateNameEn?: string | null;
  subProjectTemplates?: ISubProjectTemplate[] | null;
}

export class ProjectTemplate implements IProjectTemplate {
  constructor(
    public id?: number,
    public projectTemplateCode?: string | null,
    public projectTemplateNameAr?: string | null,
    public projectTemplateNameEn?: string | null,
    public subProjectTemplates?: ISubProjectTemplate[] | null
  ) {}
}

export function getProjectTemplateIdentifier(projectTemplate: IProjectTemplate): number | undefined {
  return projectTemplate.id;
}
