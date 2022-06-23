import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProject, Project } from '../project.model';
import { ProjectService } from '../service/project.service';
import { IProjectTemplate } from 'app/entities/project-template/project-template.model';
import { ProjectTemplateService } from 'app/entities/project-template/service/project-template.service';
import { IBuildingType } from 'app/entities/building-type/building-type.model';
import { BuildingTypeService } from 'app/entities/building-type/service/building-type.service';

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html',
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;

  projectTemplatesCollection: IProjectTemplate[] = [];
  buildingTypesCollection: IBuildingType[] = [];

  editForm = this.fb.group({
    id: [],
    clientName: [],
    enterDate: [],
    location: [],
    projectTemplate: [],
    buildingType: [],
  });

  constructor(
    protected projectService: ProjectService,
    protected projectTemplateService: ProjectTemplateService,
    protected buildingTypeService: BuildingTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      if (project.id === undefined) {
        const today = dayjs().startOf('day');
        project.enterDate = today;
      }

      this.updateForm(project);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const project = this.createFromForm();
    if (project.id !== undefined) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  trackProjectTemplateById(_index: number, item: IProjectTemplate): number {
    return item.id!;
  }

  trackBuildingTypeById(_index: number, item: IBuildingType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(project: IProject): void {
    this.editForm.patchValue({
      id: project.id,
      clientName: project.clientName,
      enterDate: project.enterDate ? project.enterDate.format(DATE_TIME_FORMAT) : null,
      location: project.location,
      projectTemplate: project.projectTemplate,
      buildingType: project.buildingType,
    });

    this.projectTemplatesCollection = this.projectTemplateService.addProjectTemplateToCollectionIfMissing(
      this.projectTemplatesCollection,
      project.projectTemplate
    );
    this.buildingTypesCollection = this.buildingTypeService.addBuildingTypeToCollectionIfMissing(
      this.buildingTypesCollection,
      project.buildingType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectTemplateService
      .query({ filter: 'project-is-null' })
      .pipe(map((res: HttpResponse<IProjectTemplate[]>) => res.body ?? []))
      .pipe(
        map((projectTemplates: IProjectTemplate[]) =>
          this.projectTemplateService.addProjectTemplateToCollectionIfMissing(projectTemplates, this.editForm.get('projectTemplate')!.value)
        )
      )
      .subscribe((projectTemplates: IProjectTemplate[]) => (this.projectTemplatesCollection = projectTemplates));

    this.buildingTypeService
      .query({ filter: 'project-is-null' })
      .pipe(map((res: HttpResponse<IBuildingType[]>) => res.body ?? []))
      .pipe(
        map((buildingTypes: IBuildingType[]) =>
          this.buildingTypeService.addBuildingTypeToCollectionIfMissing(buildingTypes, this.editForm.get('buildingType')!.value)
        )
      )
      .subscribe((buildingTypes: IBuildingType[]) => (this.buildingTypesCollection = buildingTypes));
  }

  protected createFromForm(): IProject {
    return {
      ...new Project(),
      id: this.editForm.get(['id'])!.value,
      clientName: this.editForm.get(['clientName'])!.value,
      enterDate: this.editForm.get(['enterDate'])!.value ? dayjs(this.editForm.get(['enterDate'])!.value, DATE_TIME_FORMAT) : undefined,
      location: this.editForm.get(['location'])!.value,
      projectTemplate: this.editForm.get(['projectTemplate'])!.value,
      buildingType: this.editForm.get(['buildingType'])!.value,
    };
  }
}
