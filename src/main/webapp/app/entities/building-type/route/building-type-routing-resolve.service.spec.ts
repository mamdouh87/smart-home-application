import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBuildingType, BuildingType } from '../building-type.model';
import { BuildingTypeService } from '../service/building-type.service';

import { BuildingTypeRoutingResolveService } from './building-type-routing-resolve.service';

describe('BuildingType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BuildingTypeRoutingResolveService;
  let service: BuildingTypeService;
  let resultBuildingType: IBuildingType | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(BuildingTypeRoutingResolveService);
    service = TestBed.inject(BuildingTypeService);
    resultBuildingType = undefined;
  });

  describe('resolve', () => {
    it('should return IBuildingType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBuildingType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBuildingType).toEqual({ id: 123 });
    });

    it('should return new IBuildingType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBuildingType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBuildingType).toEqual(new BuildingType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BuildingType })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBuildingType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBuildingType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
