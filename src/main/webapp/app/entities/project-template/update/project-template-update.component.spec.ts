import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectTemplateService } from '../service/project-template.service';
import { IProjectTemplate, ProjectTemplate } from '../project-template.model';

import { ProjectTemplateUpdateComponent } from './project-template-update.component';

describe('ProjectTemplate Management Update Component', () => {
  let comp: ProjectTemplateUpdateComponent;
  let fixture: ComponentFixture<ProjectTemplateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectTemplateService: ProjectTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectTemplateUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProjectTemplateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectTemplateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectTemplateService = TestBed.inject(ProjectTemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const projectTemplate: IProjectTemplate = { id: 456 };

      activatedRoute.data = of({ projectTemplate });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(projectTemplate));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProjectTemplate>>();
      const projectTemplate = { id: 123 };
      jest.spyOn(projectTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectTemplate }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectTemplateService.update).toHaveBeenCalledWith(projectTemplate);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProjectTemplate>>();
      const projectTemplate = new ProjectTemplate();
      jest.spyOn(projectTemplateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectTemplate }));
      saveSubject.complete();

      // THEN
      expect(projectTemplateService.create).toHaveBeenCalledWith(projectTemplate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProjectTemplate>>();
      const projectTemplate = { id: 123 };
      jest.spyOn(projectTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectTemplateService.update).toHaveBeenCalledWith(projectTemplate);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
