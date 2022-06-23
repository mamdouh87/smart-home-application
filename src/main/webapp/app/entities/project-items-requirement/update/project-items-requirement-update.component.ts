import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProjectItemsRequirement, ProjectItemsRequirement } from '../project-items-requirement.model';
import { ProjectItemsRequirementService } from '../service/project-items-requirement.service';
import { IRequirementItem } from 'app/entities/requirement-item/requirement-item.model';
import { RequirementItemService } from 'app/entities/requirement-item/service/requirement-item.service';
import { ISubProject } from 'app/entities/sub-project/sub-project.model';
import { SubProjectService } from 'app/entities/sub-project/service/sub-project.service';

@Component({
  selector: 'jhi-project-items-requirement-update',
  templateUrl: './project-items-requirement-update.component.html',
})
export class ProjectItemsRequirementUpdateComponent implements OnInit {
  isSaving = false;

  requirementItemsCollection: IRequirementItem[] = [];
  subProjectsSharedCollection: ISubProject[] = [];

  editForm = this.fb.group({
    id: [],
    qtyNo: [],
    notes: [],
    requirementItems: [],
    subProject: [],
  });

  constructor(
    protected projectItemsRequirementService: ProjectItemsRequirementService,
    protected requirementItemService: RequirementItemService,
    protected subProjectService: SubProjectService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectItemsRequirement }) => {
      this.updateForm(projectItemsRequirement);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectItemsRequirement = this.createFromForm();
    if (projectItemsRequirement.id !== undefined) {
      this.subscribeToSaveResponse(this.projectItemsRequirementService.update(projectItemsRequirement));
    } else {
      this.subscribeToSaveResponse(this.projectItemsRequirementService.create(projectItemsRequirement));
    }
  }

  trackRequirementItemById(_index: number, item: IRequirementItem): number {
    return item.id!;
  }

  trackSubProjectById(_index: number, item: ISubProject): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectItemsRequirement>>): void {
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

  protected updateForm(projectItemsRequirement: IProjectItemsRequirement): void {
    this.editForm.patchValue({
      id: projectItemsRequirement.id,
      qtyNo: projectItemsRequirement.qtyNo,
      notes: projectItemsRequirement.notes,
      requirementItems: projectItemsRequirement.requirementItems,
      subProject: projectItemsRequirement.subProject,
    });

    this.requirementItemsCollection = this.requirementItemService.addRequirementItemToCollectionIfMissing(
      this.requirementItemsCollection,
      projectItemsRequirement.requirementItems
    );
    this.subProjectsSharedCollection = this.subProjectService.addSubProjectToCollectionIfMissing(
      this.subProjectsSharedCollection,
      projectItemsRequirement.subProject
    );
  }

  protected loadRelationshipsOptions(): void {
    this.requirementItemService
      .query({ filter: 'projectitemsrequirement-is-null' })
      .pipe(map((res: HttpResponse<IRequirementItem[]>) => res.body ?? []))
      .pipe(
        map((requirementItems: IRequirementItem[]) =>
          this.requirementItemService.addRequirementItemToCollectionIfMissing(
            requirementItems,
            this.editForm.get('requirementItems')!.value
          )
        )
      )
      .subscribe((requirementItems: IRequirementItem[]) => (this.requirementItemsCollection = requirementItems));

    this.subProjectService
      .query()
      .pipe(map((res: HttpResponse<ISubProject[]>) => res.body ?? []))
      .pipe(
        map((subProjects: ISubProject[]) =>
          this.subProjectService.addSubProjectToCollectionIfMissing(subProjects, this.editForm.get('subProject')!.value)
        )
      )
      .subscribe((subProjects: ISubProject[]) => (this.subProjectsSharedCollection = subProjects));
  }

  protected createFromForm(): IProjectItemsRequirement {
    return {
      ...new ProjectItemsRequirement(),
      id: this.editForm.get(['id'])!.value,
      qtyNo: this.editForm.get(['qtyNo'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      requirementItems: this.editForm.get(['requirementItems'])!.value,
      subProject: this.editForm.get(['subProject'])!.value,
    };
  }
}
