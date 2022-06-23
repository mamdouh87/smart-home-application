import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RequirementItemService } from '../service/requirement-item.service';
import { IRequirementItem, RequirementItem } from '../requirement-item.model';
import { ISubProjectTemplate } from 'app/entities/sub-project-template/sub-project-template.model';
import { SubProjectTemplateService } from 'app/entities/sub-project-template/service/sub-project-template.service';

import { RequirementItemUpdateComponent } from './requirement-item-update.component';

describe('RequirementItem Management Update Component', () => {
  let comp: RequirementItemUpdateComponent;
  let fixture: ComponentFixture<RequirementItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requirementItemService: RequirementItemService;
  let subProjectTemplateService: SubProjectTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RequirementItemUpdateComponent],
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
      .overrideTemplate(RequirementItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequirementItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requirementItemService = TestBed.inject(RequirementItemService);
    subProjectTemplateService = TestBed.inject(SubProjectTemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SubProjectTemplate query and add missing value', () => {
      const requirementItem: IRequirementItem = { id: 456 };
      const subProjectTemplate: ISubProjectTemplate = { id: 57758 };
      requirementItem.subProjectTemplate = subProjectTemplate;

      const subProjectTemplateCollection: ISubProjectTemplate[] = [{ id: 63910 }];
      jest.spyOn(subProjectTemplateService, 'query').mockReturnValue(of(new HttpResponse({ body: subProjectTemplateCollection })));
      const additionalSubProjectTemplates = [subProjectTemplate];
      const expectedCollection: ISubProjectTemplate[] = [...additionalSubProjectTemplates, ...subProjectTemplateCollection];
      jest.spyOn(subProjectTemplateService, 'addSubProjectTemplateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requirementItem });
      comp.ngOnInit();

      expect(subProjectTemplateService.query).toHaveBeenCalled();
      expect(subProjectTemplateService.addSubProjectTemplateToCollectionIfMissing).toHaveBeenCalledWith(
        subProjectTemplateCollection,
        ...additionalSubProjectTemplates
      );
      expect(comp.subProjectTemplatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requirementItem: IRequirementItem = { id: 456 };
      const subProjectTemplate: ISubProjectTemplate = { id: 84805 };
      requirementItem.subProjectTemplate = subProjectTemplate;

      activatedRoute.data = of({ requirementItem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(requirementItem));
      expect(comp.subProjectTemplatesSharedCollection).toContain(subProjectTemplate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequirementItem>>();
      const requirementItem = { id: 123 };
      jest.spyOn(requirementItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requirementItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requirementItem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(requirementItemService.update).toHaveBeenCalledWith(requirementItem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequirementItem>>();
      const requirementItem = new RequirementItem();
      jest.spyOn(requirementItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requirementItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requirementItem }));
      saveSubject.complete();

      // THEN
      expect(requirementItemService.create).toHaveBeenCalledWith(requirementItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RequirementItem>>();
      const requirementItem = { id: 123 };
      jest.spyOn(requirementItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requirementItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requirementItemService.update).toHaveBeenCalledWith(requirementItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSubProjectTemplateById', () => {
      it('Should return tracked SubProjectTemplate primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSubProjectTemplateById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
